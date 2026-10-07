<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Add Book | Book Exchange Portal</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>

    <body>
        <nav class="topbar dashboard-nav">
            <div class="logo">Book Exchange Portal</div>
            <div class="nav-links">
                <a href="dashboard">Home</a>
                <a href="add-book">Add Book</a>
                <a href="logout">Logout</a>
            </div>
        </nav>

        <div class="page-wrap small-wrap">
            <div class="auth-card wide">
                <h2>Add a Book</h2>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert error">
                        <%= request.getAttribute("error") %>
                    </div>
                    <% } %>
                        <form action="add-book" method="post" enctype="multipart/form-data">
                            <h3>Basic Book Details</h3>
                            <div class="two-col">
                                <div>
                                    <label>Title</label>
                                    <input type="text" name="title" required>
                                </div>
                                <div>
                                    <label>Author</label>
                                    <input type="text" name="author" required>
                                </div>
                            </div>

                            <div class="two-col">
                                <div>
                                    <label>ISBN</label>
                                    <input type="text" name="isbn" required>
                                </div>
                                <div>
                                    <label>Publisher</label>
                                    <input type="text" name="publisher">
                                </div>
                            </div>

                            <div class="two-col">
                                <div>
                                    <label>Publication Year</label>
                                    <input type="number" name="publicationYear" min="1000" max="2100">
                                </div>
                                <div>
                                    <label>Language</label>
                                    <input type="text" name="language" value="English">
                                </div>
                            </div>

                            <div class="two-col">
                                <div>
                                    <label>Category / Genre</label>
                                    <select name="category" required>
                                        <option value="Academic">Academic</option>
                                        <option value="Novel">Novel</option>
                                        <option value="Competitive Exam">Competitive Exam</option>
                                        <option value="Engineering">Engineering</option>
                                        <option value="Medical">Medical</option>
                                        <option value="School">School</option>
                                        <option value="Comics">Comics</option>
                                        <option value="Others">Others</option>
                                    </select>
                                </div>
                                <div>
                                    <label>Edition</label>
                                    <input type="text" name="edition">
                                </div>
                            </div>

                            <h3>Book Condition</h3>
                            <div class="two-col">
                                <div>
                                    <label>Condition</label>
                                    <select name="condition">
                            <option value="New">New</option>
                            <option value="Like New">Like New</option>
                            <option value="Good">Good</option>
                            <option value="Fair">Fair</option>
                            <option value="Poor">Poor</option>
                        </select>
                                </div>
                                <div>
                                    <label>Condition Details</label>
                                    <select name="conditionDetails">
                                        <option value="No marks/highlights">No marks/highlights</option>
                                        <option value="Few highlights">Few highlights</option>
                                        <option value="Notes written inside">Notes written inside</option>
                                    </select>
                                </div>
                            </div>
                            <div class="two-col">
                                <label><input type="checkbox" name="missingPages" value="yes"> Missing pages</label>
                                <label><input type="checkbox" name="damagedCover" value="yes"> Damaged cover</label>
                            </div>

                            <h3>Pricing and Availability</h3>
                            <div class="two-col">
                                <div><label>Original Price</label><input type="number" name="originalPrice" step="0.01" min="0"></div>
                                <div><label>Selling Price</label><input type="number" name="sellingPrice" step="0.01" min="0" required></div>
                            </div>
                            <div class="two-col">
                                <div><label>Sale Type</label><select name="saleMode"><option>Exchange Only</option><option>Sell Only</option><option>Sell or Exchange</option></select></div>
                                <div><label>Status</label><select name="status"><option>Available</option><option>Reserved</option><option>Sold</option></select></div>
                            </div>
                            <label><input type="checkbox" name="negotiable" value="yes"> Price is negotiable</label>

                            <h3>Book Images</h3>
                            <label>Upload up to 5 photos (front, back, inside pages, or other)</label>
                            <input type="file" name="image1" accept="image/*">
                            <input type="file" name="image2" accept="image/*">
                            <input type="file" name="image3" accept="image/*">
                            <input type="file" name="image4" accept="image/*">
                            <input type="file" name="image5" accept="image/*">

                            <h3>Exchange Preferences</h3>
                            <div class="two-col">
                                <div><label>Preferred Category</label><input type="text" name="preferredCategory"></div>
                                <div><label>Preferred Author</label><input type="text" name="preferredAuthor"></div>
                            </div>
                            <label>Preferred Subject</label><input type="text" name="preferredSubject">

                            <h3>Seller Information</h3>
                            <div class="two-col">
                                <div><label>Seller Name</label><input type="text" value="${sessionScope.user.fullName}" readonly></div>
                                <div><label>Email ID</label><input type="email" value="${sessionScope.user.email}" readonly></div>
                            </div>
                            <div class="two-col">
                                <div><label>College / University</label><input type="text" name="college"></div>
                                <div><label>Course / Branch</label><input type="text" name="branch"></div>
                            </div>
                            <div class="two-col">
                                <div><label>Year / Semester</label><input type="text" name="yearSemester"></div>
                                <div><label>Contact Number</label><input type="text" value="${sessionScope.user.phone}" readonly></div>
                            </div>
                            <label>Location / City</label><input type="text" name="location">

                            <h3>Delivery Options</h3>
                            <select name="deliveryOptions">
                                <option>Meet in Person</option><option>College Campus Pickup</option><option>Home Pickup</option><option>Courier Available</option>
                            </select>

                            <h3>Description</h3>
                            <label>Additional Notes</label><textarea name="additionalNotes" rows="3"></textarea>
                            <label>Description</label><textarea name="description" rows="4"></textarea>
                            <div class="two-col">
                                <div><label>Reason for Selling</label><input type="text" name="sellingReason"></div>
                                <div><label>Book Usage Duration</label><input type="text" name="usageDuration" placeholder="e.g. 2 years"></div>
                            </div>

                            <button type="submit" class="btn primary full">Add Book</button>
                        </form>
            </div>
        </div>
    </body>

    </html>