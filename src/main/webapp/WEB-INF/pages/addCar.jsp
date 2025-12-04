<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add Car">
    <h1>Add Car</h1>

    <form class="needs-validation" novalidate method="POST"
          action="${pageContext.request.contextPath}/AddCar">

        <!-- License Plate -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="license_plate" class="form-label">License Plate</label>
                <input type="text"
                       class="form-control"
                       id="license_plate"
                       name="license_plate"
                       required>
                <div class="invalid-feedback">
                    License Plate is required.
                </div>
            </div>
        </div>

        <!-- Parking Spot -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="parking_spot" class="form-label">Parking Spot</label>
                <input type="text"
                       class="form-control"
                       id="parking_spot"
                       name="parking_spot"
                       required>
                <div class="invalid-feedback">
                    Parking Spot is required.
                </div>
            </div>
        </div>

        <!-- Owner -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="owner_id" class="form-label">Owner</label>
                <select name="owner_id" class="form-select">
                    <option value="">Choose owner</option>
                    <c:forEach var="u" items="${users}">
                        <option value="${u.id}">
                                ${u.username}
                        </option>
                    </c:forEach>
                </select>

                <div class="invalid-feedback">
                    Please select an owner.
                </div>
            </div>
        </div>

        <!-- Save Button -->
        <button type="submit" class="btn btn-primary btn-lg">Save</button>

    </form>
</t:pageTemplate>
