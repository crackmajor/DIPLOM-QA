FROM node:16-alpine

WORKDIR /usr/src/app
COPY ./artifacts/gate-simulator/package*.json ./
COPY app.js ./
COPY data.json ./

RUN npm install

COPY ../.. .

EXPOSE 9999
CMD ["npm", "start"]