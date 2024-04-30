import requests
import random
import string

url = "http://localhost:5000/api/post"

def generate_random_string(length):
    letters = string.ascii_letters
    return ''.join(random.choice(letters) for _ in range(length))

for _ in range(1000):
    key = generate_random_string(50)  # Generate a random string of length 50
    value = 'value' + key
    params = {'key': key, 'value': value}
    response = requests.post(url, params=params)
    print(f"Response for key={key}: {response.status_code}")
