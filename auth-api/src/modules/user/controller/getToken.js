import getTokenService from "../services/getToken.js";

async function getToken(req, res) {
    let token = await getTokenService(req);
    return res.status(token.status).json(token);
}

export default getToken;