/// <reference types="cypress" />

const baseUrl = Cypress.config().baseurl;

describe("POST", () => {
    it("should be able to create a new gif/image", () => {
        cy.request({
            method: "POST",
            url: `/plugins/servlet/gifrepo/api/gif`,
            form: true,
            body: {
                name : "example",
                url: "https://i.imgur.com/6OMgQ.jpeg"
            }
        });
    });

});

describe("GET", () => {
    before("should be able to create a new gif/image", () => {
        cy.request({
            method: "POST",
            url: `/plugins/servlet/gifrepo/api/gif`,
            form: true,
            body: {
                name : "example",
                url: "https://i.imgur.com/6OMgQ.jpeg"
            }
        });
    });

    it("should be able to get gif/images", () => {
        cy.request({
            method: "GET",
            url: `/plugins/servlet/gifrepo/api/gif`
        });
    });

});