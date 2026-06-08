#Running 30s test @ http://localhost:8080/api/v1/books
#  8 threads and 100 connections
#  Thread Stats   Avg      Stdev     Max   +/- Stdev
#    Latency    10.70ms   65.85ms   1.24s    98.54%
#    Req/Sec     3.21k   313.05     5.26k    93.31%
#  Latency Distribution
#     50%    3.80ms
#     75%    4.08ms
#     90%    4.82ms
#     99%  248.93ms
#  764155 requests in 30.01s, 6.19GB read
#Requests/sec:  25466.16
#Transfer/sec:    211.37MB

TOKEN=$(curl -s -X POST http://127.0.0.1:8080/api/v1/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"123456"}' | python3 -c "import sys,json; print(json.load(sys.stdin)['accessToken'])")

wrk -t8 -c100 -d30s --latency -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/v1/books
