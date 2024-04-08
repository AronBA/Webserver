const axios = require('axios');

const serverUrl = 'http://localhost:8080/'; // Replace this with your server address
const numberOfRequests = 5000; // Number of requests to send
const concurrency = 1000; // Number of concurrent requests to send

let completedRequests = 0;
let startTime = Date.now();

async function sendRequests() {
    console.log(`Sending ${numberOfRequests} requests to ${serverUrl}`);

    for (let i = 0; i < numberOfRequests; i++) {
        axios.get(serverUrl)
            .then(() => {
                completedRequests++;
                printStats();
            })
            .catch(error => {
                console.error(`Request failed: ${error.message}`);
                completedRequests++;
                printStats();
            });

        if (i % concurrency === 0) {
            // Wait for all concurrent requests to finish
            await new Promise(resolve => setTimeout(resolve, 100));
        }
    }
}

function printStats() {
    const elapsedTime = (Date.now() - startTime) / 1000; // in seconds
    const requestsPerSecond = completedRequests / elapsedTime;
    console.log(`Completed ${completedRequests} requests in ${elapsedTime} seconds. Requests per second: ${requestsPerSecond.toFixed(2)}`);
}

sendRequests();
