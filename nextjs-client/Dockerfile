FROM node:alpine

WORKDIR /app

COPY . /app

RUN npm install

# start app
RUN npm run build
EXPOSE 3000
CMD npm run start