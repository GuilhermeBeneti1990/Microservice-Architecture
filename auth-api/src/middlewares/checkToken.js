import jwt from "jsonwebtoken";
import { promisify } from "util";
import * as httpStatus from "../config/constants/httpStatus.js"
import * as secrets from "../config/constants/secret.js"
import AuthException from "../modules/user/exceptions/AuthException.js";

const bearer = "bearer ";
const emptySpace = " ";

export default async (req, res, next) => {
    try {
        const { authorization } = req.headers;
        if(!authorization) {
            throw new AuthException(httpStatus.UNAUTHORIZED, "Token invalid!");
        }

        let token = authorization;
        if(token.includes(emptySpace)) {
            token = token.split(emptySpace)[1];
        }

        const decodedToken = await promisify(jwt.verify)(token, secrets.apiSecret);

        req.authUser = decodedToken.authUser;

        return next();
    } catch (error) {
        const status = error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR
        return res.status(status).json({
            status,
            message: error.message
        });
    }
}