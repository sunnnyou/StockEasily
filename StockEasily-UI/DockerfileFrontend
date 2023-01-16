FROM node:19.0.1

WORKDIR /app

COPY package*.json /app/

RUN npm install

COPY . /app/

ENV HOST=localhost

EXPOSE 5173

CMD ["npm", "run", "dev"]