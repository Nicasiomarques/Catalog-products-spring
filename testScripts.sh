#!/bin/bash
# CRUD Test Script for Product API

# Base URL of the API
BASE_URL="http://localhost:8080/products"

# Function to make a request and display the response
make_request() {
  local endpoint="$1"
  local method="$2"
  local payload="$3"

  echo "Endpoint: $endpoint"
  echo "Request:"
  echo "curl -X $method -H 'Content-Type: application/json' -d '$payload' $BASE_URL/$endpoint"
  
  response=$(curl -s -w "\nHTTP Response Code: %{http_code}\n" -X $method -H 'Content-Type: application/json' -d "$payload" $BASE_URL/$endpoint)
  
  echo -e "\nResponse:\n$response"
  echo "------------------------------------------------------"

  # Check if the response code indicates success (2xx)
  [[ $response =~ HTTP\ Response\ Code:\ 2[0-9][0-9] ]] && echo "✅ Operation successful" || echo "❌ Operation failed"
}

# Payloads
create_payload='{
  "name": "New Product",
  "description": "This is a new product.",
  "price": 50.0,
  "imgUrl": "https://example.com/image.jpg",
  "date": "2024-02-16T12:00:00Z",
  "categories": [{"id": 1, "name": "Electronics"}]
}'

update_payload='{
  "name": "Updated Product",
  "description": "This product has been updated.",
  "price": 60.0,
  "imgUrl": "https://example.com/updated-image.jpg",
  "date": "2024-02-16T14:30:00Z",
  "categories": [{"id": 2, "name": "Books"}]
}'

# Main execution
make_request "Create (POST)" "POST" "$create_payload"
make_request "Read (GET)" "GET"
make_request "Update (PUT)" "PUT" "$update_payload"
make_request "Delete (DELETE)" "DELETE"
