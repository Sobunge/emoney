
const adminInput = document.getElementById("ADMIN");
const landlordInput = document.getElementById("LANDLORD")
const tenantInput = document.getElementById("TENANT");
const userInput = document.getElementById("USER");

adminInput.addEventListener("change", adminRoleToggle);
landlordInput.addEventListener("change", landlordRoleToggle);
tenantInput.addEventListener("change", tenantRoleToggle);
userInput.addEventListener("change", userRoleToggle);

function adminRoleToggle() {

    if (adminInput.checked) {
        alert("Admin selected");
    } else {
        alert("Admin deselected");
    }

}

function landlordRoleToggle() {

    if (landlordInput.checked) {
        alert("Landlord selected");
    } else {
        alert("Landlord deselected");
    }

}

function tenantRoleToggle() {

    if (tenantInput.checked) {
        alert("Tenant selected");
    } else {
        alert("Tenant deselected");
    }
}

function userRoleToggle() {

    if (userInput.checked) {
        alert("User selected");
    } else {
        alert("User deselected");
    }
}