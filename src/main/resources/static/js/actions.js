
// Role selection toggle

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
    } else {
        document.getElementById("LandlordInput").style.display = "";
        document.getElementById("TenantInput").style.display = "";
        document.getElementById("UserInput").style.display = "";
    }

}

function landlordRoleToggle() {

    if (landlordInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
        document.getElementById("TenantInput").style.display = "none";
        tenantInput.checked = false;
    } else {
        if (userInput.checked) {
            document.getElementById("AdminInput").style.display = "none";
        } else {
            document.getElementById("AdminInput").style.display = "";
        }
        document.getElementById("TenantInput").style.display = "";
    }

}

function tenantRoleToggle() {

    if (tenantInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
        document.getElementById("LandlordInput").style.display = "none";
        landlordInput.checked = false;
    } else {
        if (userInput.checked) {
            document.getElementById("AdminInput").style.display = "none";
        } else {
            document.getElementById("AdminInput").style.display = "";
        }
        document.getElementById("LandlordInput").style.display = "";
    }
}

function userRoleToggle() {

    if (userInput.checked) {
        document.getElementById("AdminInput").style.display = "none";
        adminInput.checked = false;
    } else {
        if (tenantInput.checked || landlordInput.checked) {
            document.getElementById("AdminInput").style.display = "none";
        } else {
            document.getElementById("AdminInput").style.display = "";
        }
    }
}

// Password change toggle

const toggleCurrentPassword = document.querySelector("#toggleCurrentPassword");
const toggleNewPassword = document.querySelector("#toggleNewPassword");
const toggleRepeatNewPassword = document.querySelector("#toggleRepeatNewPassword");

const currentPasswordInput = document.querySelector("#currentPasswordInput");
const newPasswordInput = document.querySelector("#newPasswordInput");
const repeatNewPasswordInput = document.querySelector("#repeatNewPasswordInput");

toggleCurrentPassword.addEventListener("click", function () {
    // toggle the type attribute
    const type = currentPasswordInput.getAttribute("type") === "password" ? "text" : "password";
    currentPasswordInput.setAttribute("type", type);

    // toggle the icon
    this.classList.toggle("bi-eye");
});

toggleNewPassword.addEventListener("click", function () {
    // toggle the type attribute
    const type = newPasswordInput.getAttribute("type") === "password" ? "text" : "password";
    newPasswordInput.setAttribute("type", type);

    // toggle the icon
    this.classList.toggle("bi-eye");
});

toggleRepeatNewPassword.addEventListener("click", function () {
    // toggle the type attribute
    const type = repeatNewPasswordInput.getAttribute("type") === "password" ? "text" : "password";
    repeatNewPasswordInput.setAttribute("type", type);

    // toggle the icon
    this.classList.toggle("bi-eye");
});
