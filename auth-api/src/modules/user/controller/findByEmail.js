import findByEmailService from "../services/findByEmail.js";

async function findByEmail(req, res) {
    let user = await findByEmailService(req);
    return res.status(user.status).json(user);
}

export default findByEmail;