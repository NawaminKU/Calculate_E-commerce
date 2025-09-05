# Calculate_E-commerce
เป้าหมาย : สร้างระบบจำลองการประมวลผลคำสั่งซื้อที่สมบูรณ์ โดยประยุกต์ใช้ Design Patterns 4 รูปแบบเพื่อจัดการกับส่วนต่างๆ ของระบบ: การคำนวณราคา, การสร้างการจัดส่ง, การเพิ่มบริการเสริม, และการแจ้งเตือน

ภาพรวมของระบบ
   - ลูกค้าสร้างคำสั่งซื้อ (Order)
   - ระบบคำนวณราคาสุดท้ายโดยใช้ กลยุทธ์ส่วนลด (Strategy) ที่แตกต่างกัน
   - ระบบสร้างประเภทการจัดส่ง (Shipment) ที่เหมาะสมโดยใช้ โรงงาน (Factory)
   - ลูกค้าสามารถเพิ่ม บริการเสริม (Decorator) ให้กับการจัดส่งได้ เช่น การห่อของขวัญ
   - เมื่อยืนยันคำสั่งซื้อ ระบบจะแจ้งเตือนไปยังส่วนอื่นๆ ที่เกี่ยวข้องโดยใช้ ผู้สังเกตการณ์ (Observer)

ส่วนที่ 1 : การวางรากฐาน (Data Models)
สร้างคลาสพื้นฐานสำหรับเก็บข้อมูล (คุณสามารถใช้ record เพื่อความกระชับ):
   - Product.java: record Product(String id, String name, double price)
   - Order.java: record Order(String orderId, List<Product> products, String customerEmail) มีเมธอด double getTotalPrice() ส่งผลรวมของราคาสินค้า

ส่วนที่ 2 : การคำนวณราคา (Strategy Pattern)
ในส่วนนี้ คุณจะสร้างระบบส่วนลดที่ยืดหยุ่น
    1. สร้าง DiscountStrategy (Interface):
        - มีเมธอด double applyDiscount(Order order)
    2. สร้าง Concrete Strategies:      
       2.1 PercentageDiscount: implement DiscountStrategy โดยลดราคาตามเปอร์เซ็นต์ที่กำหนดใน constructor (เช่น 10%)    
       2.2 FixedDiscount: implement DiscountStrategy โดยลดราคาตามจำนวนเงินที่กำหนด (เช่น ลด 100 บาท)
    3. สร้าง OrderCalculator (Context):
       - มีเมธอด double calculateFinalPrice(Order order, DiscountStrategy strategy) ที่รับกลยุทธ์เข้าไปแล้วคำนวณราคาสุดท้าย

ส่วนที่ 3 : การสร้างการจัดส่ง (Factory Method Pattern)
ในส่วนนี้ คุณจะสร้าง "โรงงาน" สำหรับสร้างประเภทการจัดส่ง
    1. สร้าง Shipment (Interface):    
       - มีเมธอด String getInfo() และ double getCost()
    2. สร้าง Concrete Shipments:  
       2.1 StandardShipment: implement Shipment (เช่น ค่าส่ง 50 บาท, "Standard Delivery")    
       2.2 ExpressShipment: implement Shipment (เช่น ค่าส่ง 150 บาท, "Express Delivery")
    3. สร้าง ShipmentFactory:      
       - มีเมธอด Shipment createShipment(String type) ที่คืนค่า StandardShipment หรือ ExpressShipment ตาม type ที่ส่งเข้ามา ("STANDARD" หรือ "EXPRESS")

ส่วนที่ 4 : การเพิ่มบริการเสริม (Decorator Pattern)
ในส่วนนี้ คุณจะสร้าง "เสื้อคลุม" เพื่อเพิ่มบริการเสริมให้กับการจัดส่ง
    1. สร้าง ShipmentDecorator (Abstract Class):
       - ให้ implements Shipment    
       - มี protected Shipment wrappedShipment; และ constructor เพื่อรับ Shipment ที่จะถูกห่อ
    2. สร้าง Concrete Decorators:
        2.1 GiftWrapDecorator: extends ShipmentDecorator          
            - Override getInfo() เพื่อเพิ่มข้อความ "+ Gift Wrapped" ต่อท้าย        
            - Override getCost() เพื่อบวกค่าห่อของขวัญเพิ่ม (เช่น 50 บาท)
        2.2 InsuranceDecorator: extends ShipmentDecorator          
            - Override getInfo() เพื่อเพิ่มข้อความ "+ Insurance" ต่อท้าย
            - Override getCost() เพื่อบวกค่าประกันเพิ่ม (เช่น 10% ของราคาสินค้าใน Order)

ส่วนที่ 5 : การแจ้งเตือน (Observer Pattern)
ในส่วนนี้ คุณจะสร้างระบบแจ้งเตือนเมื่อมีคำสั่งซื้อใหม่
    1. สร้าง OrderObserver (Interface):    
       - มีเมธอด void update(Order order)
    2. สร้าง Concrete Observers:    
       2.1 InventoryService: implement OrderObserver โดยเมธอด update จะพิมพ์ว่า "Inventory updated for order [orderId]"  
       2.2 EmailService: implement OrderObserver โดยเมธอด update จะพิมพ์ว่า "Confirmation email sent to [customerEmail]"
    3. สร้าง OrderProcessor (Subject/Publisher):
       - มี ArrayList<OrderObserver>  
       - มีเมธอด register(OrderObserver observer) และ unregister(OrderObserver observer)      
       - มีเมธอด processOrder(Order order) ซึ่งหลังจากทำงานเสร็จ จะต้องวนลูปเรียก update ของ observer ทุกตัว

ส่วนที่ 6: ประกอบร่างและทดสอบ (Test Runner)
สร้างคลาสที่มีเมธอด main เพื่อจำลองการทำงานทั้งหมด:
    1. ตั้งค่า: สร้าง Product, Order, OrderCalculator, ShipmentFactory, OrderProcessor และ Observer ต่างๆ
    2. คำนวณราคา: ทดลองคำนวณราคาสุดท้ายของ Order โดยใช้ DiscountStrategy ทั้งสองแบบ
    3. สร้างการจัดส่ง: ใช้ ShipmentFactory เพื่อสร้าง StandardShipment
    4. เพิ่มบริการเสริม: "ห่อ" StandardShipment ที่สร้างขึ้นด้วย GiftWrapDecorator และ InsuranceDecorator
    5. พิมพ์สรุป: แสดงข้อมูลการจัดส่งและค่าใช้จ่ายทั้งหมด (ค่าสินค้าหลังหักส่วนลด + ค่าจัดส่งพร้อมบริการเสริม)
    6. ยืนยันคำสั่งซื้อ: เรียก orderProcessor.processOrder(order) แล้วสังเกตผลลัพธ์ว่า Observer ทั้งสองตัวทำงานหรือไม่