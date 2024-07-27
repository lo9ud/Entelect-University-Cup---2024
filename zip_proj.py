import os
import zipfile

if os.path.exists("solution.zip"):
    os.remove("solution.zip")

with zipfile.PyZipFile("solution.zip", mode="w") as zf:
    for file in os.listdir("./"):
        if file.endswith(".java"):
            zf.write(file)
            print(f"Added {file} to zip file")