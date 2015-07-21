
start "" gulp clean

timeout 5 > NUL

start "" gulp server_dev

timeout 10 > NUL

start "" "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" "http://localhost:9000" --disable-web-security -incognito 