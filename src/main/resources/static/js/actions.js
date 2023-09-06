
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
        document.getElementById("LandlordInput").style.display = "none";
        landlordInput.checked = false;
        document.getElementById("TenantInput").style.display = "none";
        tenantInput.checked = false;
        document.getElementById("UserInput").style.display = "none";
        userInput.checked = false;
        alert("Admin selected");
    } else {
        document.getElementById("LandlordInput").style.display = "";
        document.getElementById("TenantInput").style.display = "";
        document.getElementById("UserInput").style.display = "";
        alert("Admin deselected");
    }

}

function landlordRoleToggle() {

    if (landlordInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
        document.getElementById("TenantInput").style.display = "none";
        tenantInput.checked = false;
        alert("Landlord selected");
    } else {
        document.getElementById("AdminInput").style.display = "";
        document.getElementById("TenantInput").style.display = "";
        alert("Landlord deselected");
    }

}

function tenantRoleToggle() {

    if (landlordInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
        document.getElementById("TenantInput").style.display = "none";
        tenantInput.checked = false;
        alert("Landlord selected");
    } else {
        document.getElementById("AdminInput").style.display = "";
        document.getElementById("TenantInput").style.display = "";
        alert("Landlord deselected");
    }
}

function userRoleToggle() {

    if (userInput.checked) {
        alert("User selected");
    } else {
        alert("User deselected");
    }
}