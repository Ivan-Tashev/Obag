const ROUTE_PREFIX = "http://localhost:8080/logs"
let logsTableElement = document.getElementById("logs-table");
const usersBtnElement = document.getElementById("users-btn");
const productsBtnElement = document.getElementById("products-btn");
const pageViewsBtnElement = document.getElementById("page-views-btn");

const userBehaviour = [];
const productsTopViews = [];
const pageViews = [];

fetch(ROUTE_PREFIX + "/users")
    .then(response => response.json())
    .then(data => {
        data.forEach(user => userBehaviour.push(user))
    });

usersBtnElement.addEventListener('click', (e) => {
    logsTableElement.innerHTML = '';
    let tableConstruct = `
        <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">User</th>
                    <th scope="col">Product Name</th>
                    <th scope="col">Action</th>
                    <th scope="col">Date Time</th>
                </tr>
                </thead>
                <tbody>`;
    userBehaviour.forEach(log => {
        tableConstruct += (`
                <tr>
                    <td>${log.id}</td>
                    <td>${log.user}</td>
                    <td>${log.productName}</td>
                    <td>${log.action}</td>
                    <td>${log.createdOn.substr(0, 10)}</td>
                </tr>
        `)
    })
    tableConstruct += (`</tbody></table>`)
    logsTableElement.innerHTML = tableConstruct;
});

async function getTopViewProducts() {
    const response = await fetch(ROUTE_PREFIX + "/products");
    const data = await response.json();

    data.forEach(product => productsTopViews.push(product));
}

getTopViewProducts();

productsBtnElement.addEventListener('click', (e) => {
    logsTableElement.innerHTML = '';
    let tableConstruct = `
    <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Product Name</th>
                    <th scope="col">SKU</th>
                    <th scope="col">Category</th>
                    <th scope="col">Season</th>
                    <th scope="col">Total views</th>
                    <th scope="col">Price</th>
                </tr>
                </thead>
                <tbody>
    `;
    productsTopViews.forEach(log => {
        tableConstruct += `
                <tr>
                    <td>${log.id}</td>
                    <td>${log.name}</td>
                    <td>${log.sku}</td>
                    <td>${log.category}</td>
                    <td>${log.season}</td>
                    <td>${log.totalName}</td>
                    <td>${log.price}</td>
                </tr>
        `;
    })
    tableConstruct += (`</tbody></table>`)
    logsTableElement.innerHTML = tableConstruct;
})

async function getPageViews() {
    const response = await fetch(ROUTE_PREFIX + "/views");
    const data = await response.json();

    data.forEach(view => pageViews.push(view));
}
getPageViews();

pageViewsBtnElement.addEventListener("click", (ev) => {
    logsTableElement.innerHTML = '';
    let tableConstruct = `
        <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Date</th>
                    <th scope="col">Unique visitors</th>
                    <th scope="col">Total clicks</th>
                </tr>
                </thead>
                <tbody>`;
    pageViews.forEach(view => {
        tableConstruct += (`
                <tr>
                    <td>${view.id}</td>
                    <td>${view.createdOn.substr(0, 10)}</td>
                    <td>${view.uniqueVisits}</td>
                    <td>${view.totalVisits}</td>
                </tr>
        `)
    })
    tableConstruct += (`</tbody></table>`)
    logsTableElement.innerHTML = tableConstruct;
})