import concurrent.futures
import requests

SERVER_URL = 'http://localhost:8000/'

NUM_REQUESTS = 100


def send_request(url):
    try:
        response = requests.get(url)
        print(f"Response from {url}: {response.status_code}")
    except requests.exceptions.RequestException as e:
        print(f"Error occurred during request to {url}: {e}")


def main():
    urls = [SERVER_URL for _ in range(NUM_REQUESTS)]

    with concurrent.futures.ThreadPoolExecutor() as executor:
        future_to_url = {executor.submit(send_request, url): url for url in urls}
        for future in concurrent.futures.as_completed(future_to_url):
            url = future_to_url[future]
            try:
                future.result()
            except Exception as exc:
                print(f"Request to {url} generated an exception: {exc}")


if __name__ == "__main__":
    main()
