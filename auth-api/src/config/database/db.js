import Sequelize from "sequelize";

const sequelize = new Sequelize("auth-db", "root", "root", {
    host: "auth-db",
    dialect: "postgres",
    quoteIdentifiers: false,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true
    }
});

sequelize
    .authenticate()
        .then(() => console.log("AUTH-DB:::Connection has been stablished!"))
        .catch((error) => {
            console.error("AUTH-DB:::Unable to connect to database!");
            console.error(error.message);
        });

export default sequelize;
