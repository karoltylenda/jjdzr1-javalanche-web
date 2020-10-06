<form action="/forms?uploadNationalHolidays" method="post">
    <div class="col-md-6">
        <div class="form-group">
            <label for="exampleFormControlSelect1">Select user to remove:</label>
            <select class="form-control" id="exampleFormControlSelect1" name="selectedYear">
                <%for (int year = LocalDate.now().getYear(); year < LocalDate.now().plusYears(10).getYear(); year++) {%>
                <option value=<%=year%>><%=String.valueOf(year)%>
                </option>
                <%}%>
            </select>
        </div>
        <div class="button-container">
            <button class="button-position btn btn-dark" type="submit">Submit</button>
        </div>
    </div>
</form>