const socket = new WebSocket("ws://localhost:5000/order-status");

console.log("123124129476")


        socket.onopen = function() {
            console.log("✅ WebSocket Connected!");
        };

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);

            if (data.action === "NEW_ORDER") {
                console.log("📩 Received new order:", data);
                location.reload(); // Chỉ reload khi nhận đơn hàng mới
            }

            document.getElementById("status").innerText = "Order Status: " + data.status;
        };


        socket.onclose = function() {
            console.log("❌ WebSocket Disconnected!");
        };

        document.getElementById("accept").onclick = function() {
            socket.send(JSON.stringify({ action: "ACCEPT_ORDER" }));
        };

        document.getElementById("decline").onclick = function() {
            socket.send(JSON.stringify({ action: "DECLINE_ORDER" }));
        };

        document.getElementById("finish").onclick = function() {
            socket.send(JSON.stringify({ action: "ORDER_FINISH" }));
        };