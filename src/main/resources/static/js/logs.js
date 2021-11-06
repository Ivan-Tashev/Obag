let logsTableElement = document.getElementById("logs-table");
const usersBtnElement = document.getElementById("users-btn");
const productsBtnElement = document.getElementById("products-btn");

const userBehaviour = [];
const productsTopViews = [];

fetch("http://localhost:8080/logs/users")
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
                    <td>${log.createdOn}</td>
                </tr>
        `)
    })
    tableConstruct += (`</tbody></table>`)
    logsTableElement.innerHTML = tableConstruct;
});

async function getTopViewProducts() {
    const response = await fetch("http://localhost:8080/logs/products");
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
                    <th scope="col">Total count</th>
                    <th scope="col">Price</th>
                </tr>
                </thead>
                <tbody>\`;
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


