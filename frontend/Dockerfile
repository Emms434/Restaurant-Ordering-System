# Local development image: installs dependencies and runs the Vite dev
# server. Production uses a static build uploaded to S3 instead (see deploy.yml).
FROM node:20-alpine
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
EXPOSE 5173
CMD ["npm","run","dev"]
