function sendCoordinates(event) {
    const container = document.getElementById('graphContainer');
    const rect = container.getBoundingClientRect();

    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;

    const x = ((clickX - 200) / 266) * 10;
    const y = -((clickY - 200) / 266) * 10;

    document.getElementById('form:graphForm:xGraph').value = x;
    document.getElementById('form:graphForm:yGraph').value = y;

    document.getElementById('form:x').value = x;
    document.getElementById('form:y').value = 0;

    document.getElementById('form:graphForm:submit').click();

}

function getSelectedRValue() {
    const rText = document.getElementById('form:r');
    if (rText == null || !((1 <= rText.value) && (rText.value <= 4))) {
        return null;
    }
    return rText.value.replace(',', '.');
}

function updateSVG() {
    const svg = document.getElementById('graph');
    svg.style.scale = (getSelectedRValue() / 5).toString();
    updateSVGWithPoints();
}

document.addEventListener('DOMContentLoaded', updateSVGWithPoints);

function updateSVGWithPoints() {
    const svgContainer = document.getElementById('circles');
    const resultTable = document.getElementById('resultTable');

    svgContainer.innerHTML = '';

    const rows = resultTable.querySelectorAll('tbody tr');
    rows.forEach(row => {
        const x = parseFloat(row.cells[0].innerText.replace(',', '.'));
        const y = parseFloat(row.cells[1].innerText.replace(',', '.'));

        const svgX = (x * 266) / 10 + 200;
        const svgY = (-y * 266) / 10 + 200;
        const r = getSelectedRValue();
        let color = 'red';
        if (r != null) {
            color = (getColors(x, y, r) === true) ? 'green' : 'red';
        }

        if (!isNaN(x) && !isNaN(y)) {
            const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
            circle.setAttribute('cx', svgX.toFixed(4));
            circle.setAttribute('cy', svgY.toFixed(4));
            circle.setAttribute('r', "5");
            circle.setAttribute('fill', color);

            svgContainer.appendChild(circle);
        }
    });
}

function getColors(x, y, r) {
    let isHit;
    if (x >= 0 && y >= 0) isHit = y <= r - x;
    if (x < 0 && y > 0) isHit = -x <= r && (y <= r / 2);
    if (x < 0 && y < 0) isHit = ((x * x + y * y) <= (r / 2 * r / 2));
    return isHit
}
