import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import UserException from "../exceptions/UserException.js";
import UserRepository from "../repository/UserRepository.js";
import * as httpStatus from "../../../config/constants/httpStatus.js";
import * as secrets from "../../../config/constants/secret.js";

function validateRequest(email, password) {
    if(!email || !password) {
        throw new UserException(httpStatus.UNAUTHORIZED, "Email and password must be informed!");
    }
}

function validateUserNotFound(user) {
    if(!user) {
        throw new UserException(httpStatus.NOT_FOUND, "User not found!");
    }
}

async function validatePassword(password, hashedPassword) {
    if(!await bcrypt.compare(password, hashedPassword)) {
        throw new UserException(httpStatus.UNAUTHORIZED, "Password doesn't match!");
    }
}

async function getTokenService(req) {
    try {
       const { email, password } = req.body;
       validateRequest(email, password);

       let user = await UserRepository.findByEmail(email);
       validateUserNotFound(user);
       await validatePassword(password, user.password);

       const authUser = {
        id: user.id,
        name: user.name,
        email: user.email
       }

       const token = jwt.sign({ authUser }, secrets.apiSecret, { expiresIn: "1d" });
       return {
        status: httpStatus.SUCCESS,
        token
       }
    } catch (error) {
        return {
            status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
            message: error.message
        }
    }
}

export default getTokenService;