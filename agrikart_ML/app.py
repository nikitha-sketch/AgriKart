from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
import csv
from joblib import load

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "http://localhost:8080"}}, supports_credentials=True)

CSV_FILE = "crop_recommendation.csv"

# ✅ Load ML Models and Encoders (only once at startup)
try:
    fert_model = load("fertilizer_model.pkl")
    pest_model = load("pesticide_model.pkl")
    encoders = load("input_encoders.pkl")
    fertilizer_encoder = load("fertilizer_encoder.pkl")
    pesticide_encoder = load("pesticide_encoder.pkl")
    ML_MODE = True
except Exception as e:
    print("⚠️ ML models not loaded, running without prediction mode.")
    ML_MODE = False


@app.route('/')
def index():
    return "✅ AgriKart Flask API Running!"


# ✅ Add new crop to CSV
@app.route('/add_crop', methods=['POST'])
def add_crop():
    try:
        data = request.get_json()
        crop = data.get("crop", "").strip()
        soil = data.get("soil", "").strip()
        season = data.get("season", "").strip()
        region = data.get("region", "").strip()
        fertilizer = data.get("fertilizer", "").strip()
        pesticide = data.get("pesticide", "").strip()

        if not all([crop, soil, season, region, fertilizer, pesticide]):
            return jsonify({"error": "All fields are required"}), 400

        with open(CSV_FILE, 'a', newline='') as f:
            writer = csv.writer(f, delimiter='\t')  # file is tab-separated
            writer.writerow([crop, soil, season, region, fertilizer, pesticide])

        return jsonify({"message": f"✅ Crop '{crop}' added successfully!"}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# ✅ Recommend fertilizer/pesticide by exact crop match
@app.route('/recommend', methods=['POST'])
def recommend():
    try:
        data = request.get_json()
        crop = data.get("crop", "").strip().lower()

        df = pd.read_csv(CSV_FILE, delimiter='\t')
        df.columns = df.columns.str.strip()

        match = df[df["Crop"].str.lower().str.strip() == crop]

        if not match.empty:
            row = match.iloc[0]
            return jsonify({
                "crop": row["Crop"],
                "soil": row["Soil"],
                "season": row["Season"],
                "region": row["Region"],
                "fertilizer": row["Fertilizer"],
                "pesticide": row["Pesticide"]
            }), 200

        return jsonify({"error": "Crop not found."}), 404

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# ✅ ML prediction of fertilizer/pesticide
@app.route("/predict", methods=["POST"])
def predict():
    if not ML_MODE:
        return jsonify({"error": "ML prediction model not loaded."}), 503

    try:
        data = request.get_json()
        crop = data["crop"].strip().lower()
        soil = data["soil"].strip().lower()
        season = data["season"].strip().lower()
        region = data["region"].strip().lower()

        # Encode input
        input_data = {}
        for feature in ["Crop", "Soil", "Season", "Region"]:
            value = data[feature.lower()]
            if value in encoders[feature].classes_:
                input_data[feature] = encoders[feature].transform([value])[0]
            else:
                return jsonify({
                    "error": f"No exact match found for {feature}: {value}. Please enter valid value."
                }), 400

        df_input = pd.DataFrame([input_data])

        fert_pred = fert_model.predict(df_input)[0]
        pest_pred = pest_model.predict(df_input)[0]

        fert_result = fertilizer_encoder.inverse_transform([fert_pred])[0]
        pest_result = pesticide_encoder.inverse_transform([pest_pred])[0]

        return jsonify({
            "fertilizer": fert_result,
            "pesticide": pest_result
        }), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(debug=True)
