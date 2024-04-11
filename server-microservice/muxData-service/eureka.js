const os = require('os');
const Eureka = require('eureka-js-client').Eureka;

const eurekaClient = new Eureka({
    instance: {
        app: 'mux-service',
        hostName: os.hostname(), // Change this to your server's hostname
        ipAddr: '172.18.0.3', // Change this to your server's IP address
        port: {
            '$': 8086, // Change this to your server's port
            '@enabled': 'true',
        },
        vipAddress: 'mux-service', // Change this to your server's hostname
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
        // other optional settings...
    },
    eureka: {
        // eureka server host / port
        host: process.env.EUREKA_HOST,
        port: 8761,
        servicePath: '/eureka/apps/',
    },
});

module.exports = eurekaClient;