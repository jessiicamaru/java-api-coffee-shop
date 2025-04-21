document.addEventListener("DOMContentLoaded", () => {
    function updateOrderCounts() {
        const sections = [
            { id: "pending", badgeId: "pending-count" },
            { id: "preparing", badgeId: "preparing-count" },
            { id: "delivering", badgeId: "delivering-count" },
            { id: "completed", badgeId: "completed-count" },
            { id: "canceled", badgeId: "canceled-count" }
        ];

        sections.forEach(section => {
            const container = document.querySelector(`#${section.id} .container`);
            const count = container ? container.children.length : 0;
            const badge = document.getElementById(section.badgeId);
            if (badge) {
                badge.textContent = count;
            }
        });
    }

    updateOrderCounts();

    document.querySelectorAll(".sidebar-link").forEach(link => {
        link.addEventListener("click", (e) => {
            e.preventDefault();
            const targetId = link.getAttribute("href").substring(1);
            const targetSection = document.getElementById(targetId);
            if (targetSection) {
                targetSection.scrollIntoView({ behavior: "smooth" });
                // Cập nhật trạng thái active
                document.querySelectorAll(".sidebar-link").forEach(l => l.classList.remove("active"));
                link.classList.add("active");
            }
        });
    });

    // Xử lý nút "Accept Order" (stat=0)
    document.querySelectorAll(".accept-order").forEach(button => {
        button.addEventListener("click", () => {
            const listItem = button.closest("li.order-row");
            const userId = listItem?.dataset.userId;
            const orderId = listItem?.dataset.orderId;
            if (!userId || !orderId) {
                console.error("Missing userId or orderId:", { userId, orderId });
                alert("Không thể cập nhật trạng thái: Thiếu userId hoặc orderId.");
                return;
            }
            updateOrderStatus(userId, orderId, 1, listItem); // Chuyển sang stat=1 (Đang chuẩn bị)
        });
    });

    // Xử lý nút "Decline Order" (stat=0, stat=1, stat=2)
    document.querySelectorAll(".decline-order").forEach(button => {
        button.addEventListener("click", () => {
            const listItem = button.closest("li.order-row");
            const userId = listItem?.dataset.userId;
            const orderId = listItem?.dataset.orderId;
            if (!userId || !orderId) {
                console.error("Missing userId or orderId:", { userId, orderId });
                alert("Không thể cập nhật trạng thái: Thiếu userId hoặc orderId.");
                return;
            }
            updateOrderStatus(userId, orderId, 4, listItem); // Chuyển sang stat=4 (Đã hủy)
        });
    });

    // Xử lý nút "Order Finish" (stat=1)
    document.querySelectorAll(".finish-order").forEach(button => {
        button.addEventListener("click", () => {
            const listItem = button.closest("li.order-row");
            const userId = listItem?.dataset.userId;
            const orderId = listItem?.dataset.orderId;
            if (!userId || !orderId) {
                console.error("Missing userId or orderId:", { userId, orderId });
                alert("Không thể cập nhật trạng thái: Thiếu userId hoặc orderId.");
                return;
            }
            updateOrderStatus(userId, orderId, 2, listItem); // Chuyển sang stat=2 (Đang giao)
        });
    });

    function updateOrderStatus(userId, orderId, status, listItem) {
        console.log("Updating order status:", { userId, orderId, status });

        fetch("/update-order-status", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userId: userId,
                orderId: orderId,
                status: status
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to update order status: ${response.statusText}`);
            }
            console.log(`Order ${orderId} updated with status ${status} for user ${userId}`);

            let targetList;
            if (status === 1) {
                targetList = document.querySelector("#preparing .container");
            } else if (status === 2) {
                targetList = document.querySelector("#delivering .container");
            } else if (status === 4) {
                targetList = document.querySelector("#canceled .container");
            }

            if (targetList) {
                const statusElement = listItem.querySelector(".status");
                if (statusElement) {
                    statusElement.textContent = status;
                    statusElement.className = "value status";
                    if (status === 1) statusElement.classList.add("preparing");
                    else if (status === 2) statusElement.classList.add("delivering");
                    else if (status === 4) statusElement.classList.add("canceled");
                }

                // Xóa nút bấm hiện tại
                const buttonGroup = listItem.querySelector(".button-group");
                if (buttonGroup) buttonGroup.innerHTML = "";

                // Thêm nút bấm mới dựa trên trạng thái
                if (status === 1) {
                    buttonGroup.innerHTML = `
                        <button class="decline-order">Decline Order</button>
                        <button class="finish-order">Order Finish</button>
                    `;
                    // Gắn lại sự kiện cho các nút mới
                    buttonGroup.querySelector(".decline-order").addEventListener("click", () => updateOrderStatus(userId, orderId, 4, listItem));
                    buttonGroup.querySelector(".finish-order").addEventListener("click", () => updateOrderStatus(userId, orderId, 2, listItem));
                } else if (status === 2) {
                    buttonGroup.innerHTML = `<button class="decline-order">Decline Order</button>`;
                    buttonGroup.querySelector(".decline-order").addEventListener("click", () => updateOrderStatus(userId, orderId, 4, listItem));
                }

                targetList.appendChild(listItem);

                updateOrderCounts();
            }
        })
        .catch(error => {
            console.error("Error updating order status:", error);
            alert("Có lỗi xảy ra khi cập nhật trạng thái đơn hàng: " + error.message);
        });
    }


    document.getElementById("add-category-btn").addEventListener("click", () => {
        const title = document.getElementById("category-title").value.trim();
        if (!title) {
            alert("Vui lòng nhập tên category!");
            return;
        }

        fetch("/add-category", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ title })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to add category");
            }
            return response.json();
        })
        .then(category => {
            const option = document.createElement("option");
            option.value = category.id;
            option.textContent = category.title;
            categorySelect.appendChild(option);
            document.getElementById("category-title").value = "";
            alert("Thêm category thành công!");
        })
        .catch(error => {
            console.error("Error adding category:", error);
            alert("Có lỗi khi thêm category: " + error.message);
        });
    });

    document.getElementById("add-coffee-btn").addEventListener("click", () => {
        const title = document.getElementById("coffee-title").value.trim();
        const categoryId = document.getElementById("coffee-category").value;
        const cost = parseFloat(document.getElementById("coffee-cost").value);
        const photoUrl = document.getElementById("coffee-photo").value.trim();
        const description = document.getElementById("coffee-description").value.trim();

        if (!title || !categoryId || !cost || !photoUrl) {
            alert("Vui lòng điền đầy đủ thông tin coffee!");
            return;
        }

        fetch("/add-coffee", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title,
                categoryId,
                cost,
                photoUrl,
                description
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to add coffee");
            }
            document.getElementById("coffee-title").value = "";
            document.getElementById("coffee-category").value = "";
            document.getElementById("coffee-cost").value = "";
            document.getElementById("coffee-photo").value = "";
            document.getElementById("coffee-description").value = "";
            alert("Thêm coffee thành công!");
        })
        .catch(error => {
            console.error("Error adding coffee:", error);
            alert("Có lỗi khi thêm coffee: " + error.message);
        });
    });
});