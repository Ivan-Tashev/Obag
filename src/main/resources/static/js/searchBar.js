const productRowElement = document.getElementById("searchRow");
const inputValueElement = document.getElementById("searchBar");
const allProducts = [];

fetch("http://localhost:8080/api/all")
    .then(response => response.json())
    .then(data => {
        data.forEach(product => allProducts.push(product))
    })

inputValueElement.addEventListener('keyup', (e) => {
    let inputValue = inputValueElement.value.toLowerCase();
    let filteredProducts = allProducts.filter(product => product.name.toLowerCase().includes(inputValue));

    displayProducts(filteredProducts);
})

const displayProducts = (products) => {
    productRowElement.innerHTML = products.map((product) => {
        return `
                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card text-center" style="border: none">
                        <a href="/products/${product.id}">
                            <img src="${product.image}"
                                 class="card-img-top flex-box" alt="...">
                        </a>
                        <div class="card-body">
                            <h6 th:text="*{name}" class="card-title">${product.name}</h6>
                            <p th:text="|*{price}лв.|" class="card-text">${product.price}лв.</p>
                            <a href="#" class="btn btn-outline-dark btn-round">Купи</a>
                        </div>
                    </div>
                </div>
        `
    })
}

