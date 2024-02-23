FROM node:20

WORKDIR /home/node/app

ENV NODE_ENV=production

COPY package.json package-lock.json ./

COPY images ./images

RUN npm install

EXPOSE 3000

USER node

CMD ["node", "server.js"]
