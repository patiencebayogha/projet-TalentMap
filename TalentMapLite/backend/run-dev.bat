start "" mongod.exe --dbpath e:\mongodb\

SET NODE_ENV=development
SET DEBUG=TalentMapLite:*

start "" npm run build

timeout 5 > NUL

start "" npm run start