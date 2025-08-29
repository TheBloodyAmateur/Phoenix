# Stage 1: Build
FROM node:22-alpine AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Stage 2: Serve with Nginx
FROM nginx:alpine
COPY --from=build /app/dist/phoenix-frontend/browser /usr/share/nginx/html
EXPOSE 80 
CMD ["nginx", "-g", "daemon off;"]
