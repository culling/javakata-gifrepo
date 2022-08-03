/// <reference types="cypress" />
const adminUser = require("../../fixtures/users/adminUser.json");
const baseUrl = Cypress.config().baseurl;

describe("form", () => {

    it("should be able to fill out and submit a gif", () => {
        cy.login(adminUser);
        cy.visit(`/plugins/servlet/gifrepo`);
        cy.get('#name').type("example item");
        cy.get('#url').type("https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/5266/9781526646651.jpg");
        cy.get('#save-gif').click();
    });

});
