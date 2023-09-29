import { Router } from "express";
import findByEmail from "../controller/findByEmail.js";

const router = new Router();

router.get("/api/users/email/:email", findByEmail);

export default router;