import UserRepository from "../repository/UserRepository.js";
import * as httpStatus from "../../../config/constants/httpStatus.js";
import UserException from "../exceptions/UserException.js";

function validateRequest(email) {
    if(!email) {
        throw new UserException(httpStatus.BAD_REQUEST, "User email must be informed!");
    }
}

function validateUserNotFound(user) {
    if(!user) {
        throw new UserException(httpStatus.BAD_REQUEST, "User not found!");
    }
}

function validateAuthenticationUser(user, authUser) {
    if(!authUser || user.id !== authUser.id) {
        throw new UserException(httpStatus.FORBIDDEN, "Forbidden access, you only have access to your own data!");
    }
}

async function findByEmailService(req) {
    try {
        const { email } = req.params;
        const { authUser } = req;
        validateRequest(email);
        let user = await UserRepository.findByEmail(email);
        validateUserNotFound(user);
        validateAuthenticationUser(user, authUser);
        return {
            status: httpStatus.SUCCESS,
            user: {
                id: user.id,
                name: user.name,
                email: user.email
            }
        }
    } catch (error) {
        return {
            status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
            message: error.message
        }
    }
}

export default findByEmailService;