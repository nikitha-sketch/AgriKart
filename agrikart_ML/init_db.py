import sqlite3

# Connect (or create) the SQLite DB file
conn = sqlite3.connect('recommendation.db')
cursor = conn.cursor()

# Create the table if not exists
cursor.execute('''
    CREATE TABLE IF NOT EXISTS recommendations (
        crop TEXT PRIMARY KEY,
        fertilizer TEXT,
        pesticide TEXT
    )
''')

# Insert some default data (optional)
cursor.executemany('''
    INSERT OR IGNORE INTO recommendations (crop, fertilizer, pesticide)
    VALUES (?, ?, ?)
''', [
    ("Tomato", "NPK 12-6-6", "Imidacloprid"),
    ("Rice", "DAP", "Carbendazim"),
    ("Wheat", "Urea", "Chlorpyrifos")
])

conn.commit()
conn.close()
print("✅ recommendation.db initialized with sample data.")
