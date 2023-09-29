import express from "express";
import { createInitialData } from "./src/config/database/initialData.js";
import userRoutes from "./src/modules/user/routes/UserRoutes.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

createInitialData();

app.use(express.json());

app.get('/api/status', (req, res) => {
    res.status(200).json({
        service: "auth-api",
        status: "up"
    })
});

app.use(userRoutes);

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
});