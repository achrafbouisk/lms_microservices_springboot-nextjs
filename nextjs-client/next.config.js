/** @type {import('next').NextConfig} */
// Import necessary modules
// const axios = require("axios");

// // Configuration for Eureka server
// const EUREKA_SERVER_URL = "http://localhost:8761/eureka/";

// // Function to register with Eureka
// async function registerWithEureka() {
//   try {
//     const response = await axios.post(`${EUREKA_SERVER_URL}`, {
//       instance: {
//         hostName: "localhost", // Change this to your host
//         app: "APP_NAME",
//         vipAddress: "APP_NAME", // This can be the same as your app name
//         status: "UP",
//         port: {
//           $: 3000, // Change this to your application's port
//           "@enabled": "true",
//         },
//         dataCenterInfo: {
//           "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
//           name: "MyOwn",
//         },
//       },
//     });
//     console.log("Registered with Eureka:", response.data);
//   } catch (error) {
//     console.error("Error registering with Eureka:", error);
//   }
// }

// // Function to deregister from Eureka
// async function deregisterFromEureka() {
//   try {
//     const response = await axios.delete(
//       `${EUREKA_SERVER_URL}/eureka/apps/APP_NAME/INSTANCE_ID`
//     );
//     console.log("Deregistered from Eureka:", response.data);
//   } catch (error) {
//     console.error("Error deregistering from Eureka:", error);
//   }
// }

// // Register with Eureka when the application starts
// registerWithEureka();

// // Deregister from Eureka when the application shuts down
// process.on("SIGINT", () => {
//   deregisterFromEureka().finally(() => process.exit(0));
// });

const nextConfig = {
  images: {
    domains: ["utfs.io"],
  },
  output: 'standalone',
};

module.exports = nextConfig;
