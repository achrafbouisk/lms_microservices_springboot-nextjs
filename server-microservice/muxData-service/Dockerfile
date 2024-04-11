FROM node:slim
WORKDIR /mux-service
COPY . .
RUN apt-get update -y && apt-get install -y openssl
RUN npm install
CMD [ "node", "index.js" ]