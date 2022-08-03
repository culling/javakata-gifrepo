Cypress.Commands.add("login", (user = { username: "admin", password: "admin" }) => {
    cy.request("/logout.action");
});

Cypress.Commands.add("createUser", (adminUser, user = {
    username: "test",
    fullName: "test",
    email: "test@test.com",
    password: "testtest"
}) => {
    cy.login(adminUser);
    cy.visit(`/admin/users/createuser.action`);
    cy.get('[name="atl_token"]').then(tokenInput => {
        const token = tokenInput[0].value;
        cy.log("token: ", token);
        cy.request({
            url: "/admin/users/docreateuser.action",
            method: "POST",
            auth: adminUser,
            form: true,
            body: {
                "atl_token": token,
                "username": user.username,
                "fullName": user.fullName,
                "email": user.email,
                "password": user.password,
                "confirm": user.password
            }
        })
    })
})

Cypress.Commands.add("logout", () => {
    cy.visit("/logout.action?logout=true");
})

Cypress.Commands.add("login", (user) => {
    cy.visit("/login.action");
    cy.get('#os_username').type(user.username);
    cy.get('#os_password').type(user.password);
    cy.get('#loginButton').click();
})


Cypress.Commands.add("getNewUserId", (username) => {
    const id = cy.request({
        method: "POST",
        url: "/plugins/servlet/exercisetracker/api/users",
        form: true,
        body: {
            "username": username
        }
    }).then(response => {
        const user = response.body;
        const id = user._id;
        return id;
    });
    return id;
});