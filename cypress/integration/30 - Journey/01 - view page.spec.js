/// <reference types="cypress" />

const baseUrl = Cypress.config().baseurl;

describe("visit", () => {
    it("should be able to view the page", () => {
        cy.visit({
            url: `/plugins/servlet/gifrepo`,
            failOnStatusCode: true
        });
        cy.screenshot({
            name: 'visit',
            overwrite: true,
        })
    });

});
