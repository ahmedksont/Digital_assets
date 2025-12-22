from flask import Flask, request, send_file, jsonify
from PyPDF2 import PdfReader, PdfWriter
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
from io import BytesIO
from datetime import datetime

app = Flask(__name__)

def create_watermark(text: str):
    packet = BytesIO()
    c = canvas.Canvas(packet, pagesize=A4)
    c.setFont("Helvetica", 10)
    c.setFillGray(0.4)
    c.drawString(50, 30, text)
    c.save()
    packet.seek(0)
    return PdfReader(packet)

@app.route("/watermark", methods=["POST"])
def watermark_pdf():
    if "file" not in request.files or "user_id" not in request.form:
        return jsonify({"error": "file and user_id required"}), 400

    file = request.files["file"]
    user_id = request.form["user_id"]

    reader = PdfReader(file)
    writer = PdfWriter()

    watermark_text = f"Licensed to {user_id} | {datetime.utcnow().isoformat()}"

    watermark_pdf = create_watermark(watermark_text)
    watermark_page = watermark_pdf.pages[0]

    for page in reader.pages:
        page.merge_page(watermark_page)
        writer.add_page(page)

    output = BytesIO()
    writer.write(output)
    output.seek(0)

    return send_file(
        output,
        mimetype="application/pdf",
        as_attachment=False,
        download_name="watermarked.pdf"
    )

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5001)
