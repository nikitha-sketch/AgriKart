import pandas as pd

# Load your crop recommendation CSV file
df = pd.read_csv("crop_recommendation.csv")
df.columns = df.columns.str.strip()  # Strip any trailing spaces in column names

# Function to recommend fertilizers and pesticides
def recommend_inputs(crop_name):
    crop_name = crop_name.strip().lower()
    match = df[df['Crop'].str.strip().str.lower() == crop_name]

    if not match.empty:
        row = match.iloc[0]
        print(f"\n✅ Recommendations for '{row['Crop']}':")
        print(f"🌱 Fertilizer: {row['Fertilizer']}")
        print(f"🐛 Pesticide: {row['Pesticide']}")
    else:
        print("❌ Crop not found. Please check the crop name.")

# Example usage
if __name__ == "__main__":
    user_input = input("Enter crop name: ")
    recommend_inputs(user_input)
