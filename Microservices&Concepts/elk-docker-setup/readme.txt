ELK Stack with Docker (Elasticsearch + Logstash + Kibana)

This project sets up a minimal ELK (Elasticsearch, Logstash, Kibana) stack using Docker. It allows log ingestion via TCP and visualization in Kibana.

---

Project Structure
elk-docker-setup/
├── docker-compose.yml
└── logstash/
└── logstash.conf

---

Getting Started

1. Prerequisites

- Docker installed and running
- Docker Compose installed

---

2. Open the source folder.

bash:

cd elk-docker-setup

---

3. Start the ELK Stack

docker-compose up -d


---

4. Send Test Logs

Send a sample log in JSON format:

echo '{"message": "Hello ELK!", "level": "INFO", "timestamp": "2025-07-01T14:00:00"}' | nc localhost 5000

---

5. View Logs in Kibana

Open http://localhost:5601

Go to ☰ → Stack Management → Data Views

Click Create Data View

Name: app-logs-*(your log timestamp)

Time field: timestamp

Go to ☰ → Discover

Filter by time and explore your logs!

---

