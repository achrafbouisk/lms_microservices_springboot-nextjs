const express = require('express');
const bodyParser = require('body-parser');
const Mux = require('@mux/mux-node');
const { PrismaClient } = require('@prisma/client');
const eurekaClient = require('./eureka');

const app = express();
const prisma = new PrismaClient();

const mux = new Mux({
    tokenId: process.env['MUX_TOKEN_ID'],
    tokenSecret: process.env['MUX_TOKEN_SECRET'],
});

app.use(bodyParser.json());

// /api/mux-data/course
app.delete('/api/mux-data/course/:assetId', async (req, res) => {
    try {
        const { assetId } = req.params;

        await mux.video.assets.delete(assetId);
        res.json("Course/Chapters muxData Deleted");
    } catch (error) {
        console.error("[COURSE_MUXDATA_DELETE]", error);
        res.status(500).send("Internal Error");
    }
});

// /api/mux-data/delete
app.delete('/api/mux-data/delete', async (req, res) => {
    try {
        const { chapterId, videoUrl } = req.body;

        if (!chapterId || !videoUrl) {
            return res.status(400).send("Chapter ID or Video URL not provided");
        }

        if (videoUrl) {
            const existingMuxData = await prisma.muxData.findFirst({
                where: {
                    chapterId: chapterId,
                },
            });

            if (existingMuxData) {
                await mux.video.assets.delete(existingMuxData.assetId);
                await prisma.muxData.delete({
                    where: {
                        id: existingMuxData.id,
                    },
                });
            }
            res.json("Chapter muxData Deleted");
        }
    } catch (error) {
        console.error("[CHAPTER_MUXDATA_DELETE]", error);
        res.status(500).send("Internal Error");
    }
});

// /api/mux-data/get
app.get('/api/mux-data/get', async (req, res) => {
    try {
        const { chapterId } = req.body;

        if (!chapterId) {
            return res.status(400).send("Chapter ID not provided");
        }

        const muxData = await prisma.muxData.findUnique({
            where: {
                chapterId: chapterId,
            },
        });
        res.json(muxData);
    } catch (error) {
        console.error("[GET_MUXDATA_ERROR]", error);
        res.status(500).send("Internal Error");
    }
});

// Add a new route for retrieving muxData by chapterId
app.get('/api/mux-data/:chapterId', async (req, res) => {
    try {
        const { chapterId } = req.params;

        if (!chapterId) {
            return res.status(400).send("Chapter ID not provided");
        }

        const muxData = await prisma.muxData.findUnique({
            where: {
                chapterId: chapterId,
            },
        });
        res.json(muxData);
    } catch (error) {
        console.error("[GET_MUXDATA_BY_CHAPTERID_ERROR]", error);
        res.status(500).send("Internal Error");
    }
});

// Add a new route for finding the first muxData record by chapterId
app.get('/api/mux-data/find-first/:chapterId', async (req, res) => {
    try {
        const { chapterId } = req.params;

        if (!chapterId) {
            return res.status(400).send("Chapter ID not provided");
        }

        const muxData = await prisma.muxData.findFirst({
            where: {
                chapterId: chapterId,
            },
        });
        res.json(muxData);
    } catch (error) {
        console.error("[FIND_FIRST_MUXDATA_BY_CHAPTERID_ERROR]", error);
        res.status(500).send("Internal Error");
    }
});


// /api/mux-data/update
app.post('/api/mux-data/update', async (req, res) => {
    try {
        const { chapterId, videoUrl } = req.body;

        if (!videoUrl) {
            return res.status(400).json({
                message: "videoUrl is required",
                success: false,
            });
        }

        const existingMuxData = await prisma.muxData.findFirst({
            where: {
                chapterId: chapterId,
            },
        });

        if (existingMuxData) {
            await mux.video.assets.delete(existingMuxData.assetId);
            await prisma.muxData.delete({
                where: {
                    id: existingMuxData.id,
                },
            });
        }

        const asset = await mux.video.assets.create({
            input: videoUrl,
            playback_policy: "public",
            test: false,
        });

        const newMuxData = await prisma.muxData.create({
            data: {
                chapterId: chapterId,
                assetId: asset.id,
                playbackId: asset.playback_ids?.[0]?.id,
            },
        });
        res.json(newMuxData);
    } catch (error) {
        console.error("ERROR AT PATCH MUXDATA", error);
        res.status(500).json({
            message: error || "Internal server error",
            success: false,
        });
    }
});

// Start Express server
const PORT = process.env.PORT || 3000;
const server = app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});

// Register with Eureka
eurekaClient.start(error => {
    if (error) {
        console.error('Error registering with Eureka:', error);
    } else {
        console.log('Registered with Eureka');
    }
});

// Graceful shutdown
process.on('SIGINT', () => {
    console.log('Stopping server...');
    server.close(() => {
        console.log('Server stopped.');
        process.exit(0);
    });
});