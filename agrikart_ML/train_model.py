# train_model.py
import pandas as pd
from sklearn.preprocessing import LabelEncoder
from sklearn.ensemble import RandomForestClassifier
from joblib import dump

# Load dataset
df = pd.read_csv("crop_recommendation.csv", delimiter='\t')
df.columns = df.columns.str.strip()  # Strip whitespace from headers
print("Actual column names in CSV:", df.columns.tolist())  # Debug

# Drop rows with missing values just in case
df = df.dropna()

# Ensure required columns exist
required_cols = ["Crop", "Soil", "Season", "Region", "Fertilizer", "Pesticide"]
for col in required_cols:
    if col not in df.columns:
        raise ValueError(f"Missing required column: {col}")

# Features and targets
X = df[["Crop", "Soil", "Season", "Region"]]
y_fert = df["Fertilizer"]
y_pest = df["Pesticide"]

# Encode all text data
encoders = {}
for column in X.columns:
    le = LabelEncoder()
    X.loc[:, column] = le.fit_transform(X[column])
    encoders[column] = le

fertilizer_encoder = LabelEncoder()
y_fert_encoded = fertilizer_encoder.fit_transform(y_fert)

pesticide_encoder = LabelEncoder()
y_pest_encoded = pesticide_encoder.fit_transform(y_pest)

# Train models
fert_model = RandomForestClassifier()
fert_model.fit(X, y_fert_encoded)

pest_model = RandomForestClassifier()
pest_model.fit(X, y_pest_encoded)

# Save models and encoders
dump(fert_model, "fertilizer_model.pkl")
dump(pest_model, "pesticide_model.pkl")
dump(encoders, "input_encoders.pkl")
dump(fertilizer_encoder, "fertilizer_encoder.pkl")
dump(pesticide_encoder, "pesticide_encoder.pkl")

print("✅ Models and encoders saved successfully!")
