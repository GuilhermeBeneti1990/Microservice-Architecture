import { Router } from "express";
import findByEmail from "../controller/findByEmail.js";
import getToken from "../controller/getToken.js";
import checkToken from "../../../middlewares/checkToken.js";

const router = new Router();

router.post("/api/users/auth", getToken);
router.use(checkToken);
router.get("/api/users/email/:email", findByEmail);

export default router;