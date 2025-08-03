from flask import Flask, request, jsonify, make_response
from flask_cors import CORS, cross_origin
import pandas as pd
import difflib

app = Flask(__name__)
CORS(app)

CSV_FILE = "crop_recommendation.csv"

@app.route('/recommend', methods=['POST', 'OPTIONS'])
@cross_origin(origins=["http://localhost:8080"])  # Frontend origin
def recommend():
    if request.method == 'OPTIONS':
        response = make_response()
        response.headers.add("Access-Control-Allow-Origin", "http://localhost:8080")
        response.headers.add("Access-Control-Allow-Headers", "Content-Type")
        response.headers.add("Access-Control-Allow-Methods", "POST")
        return response, 200

    try:
        data = request.get_json()
        crop = data.get("crop", "").strip().lower()

        # Load CSV each time in case updated
        df = pd.read_csv(CSV_FILE)
        df.columns = df.columns.str.strip()

        # ✅ Exact match
        match = df[df["Crop"].str.lower().str.strip() == crop]
        if not match.empty:
            row = match.iloc[0]
            return jsonify({
                "crop": row["Crop"],
                "fertilizer": row["Fertilizer"],
                "pesticide": row["Pesticide"]
            }), 200

        # ✅ Similar match using difflib
        crop_list = df["Crop"].str.lower().tolist()
        closest = difflib.get_close_matches(crop, crop_list, n=1)

        if closest:
            similar_row = df[df["Crop"].str.lower() == closest[0]].iloc[0]
            return jsonify({
                "crop": crop,
                "fertilizer": similar_row["Fertilizer"],
                "pesticide": similar_row["Pesticide"]
            }), 200

        # ❌ No match at all
        return jsonify({"error": "Crop not found."}), 404

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)
