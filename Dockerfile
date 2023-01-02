FROM node:16-alpine

WORKDIR /usr/src/app
COPY ./artifacts/gate-simulator/package*.json ./
COPY ./artifacts/gate-simulator/app.js ./
COPY ./artifacts/gate-simulator/data.json ./

RUN npm install

COPY . .

EXPOSE 9999
CMD ["npm", "start"]