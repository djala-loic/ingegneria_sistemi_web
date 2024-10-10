<%@ page import="com.esame.kit.model.mo.Template" %>
<div class=" mt-3 mb-3">
    <div class="form" >
        <h3>New Template</h3>
        <form id="Register" name="RegisterTemplate" action="Dispatcher" method="post">

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-addon1">title</span>
                <input type="text" name="title" maxlength="50" minlength="3"  id="title" required class="form-control"  aria-label="title" aria-describedby="basic-addon1">
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text">Description</span>
                <textarea class="form-control" aria-label="Description"  name="description"  id="description" maxlength="500" required></textarea>
            </div>

            <%--<div class="input-group mb-3 mt-3">
                <span class="input-group-text">Code</span>
                <textarea class="form-control" aria-label="Description"   name="code"  id="code" required></textarea>
            </div>--%>

            <!--<div  class="mb-3 mt-3">
                <label for="code">Code</label>
                 <textarea name="code"  id="code" required></textarea>
                <span>required</span>

           <div id ="code" style="background: white; border: 1px solid #193354;border-radius: 5px;" class="form-control"></div>
            </div>-->

            <div  class="mb-3 mt-3">
                <fieldset style="padding: 1rem">
                    <legend>Language</legend>
                    <select class="form-select" aria-label="Language:" required id="language" name="language" required>
                        <%for(String language: Template.LANGUAGELIST){%>
                        <option value="<%=language%>"><%=language%></option>
                        <%}%>
                    </select>
                </fieldset>
            </div>

            <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                </symbol>
                <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
                </symbol>
                <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </symbol>
            </svg>

            <div class="alert alert-primary d-flex align-items-center mb-3 mt-3" role="alert">
                <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Info:"><use xlink:href="#info-fill"/></svg>
                <div>
                    Link Embed del video esplicativo caricato su youtube <span style="color: #cccccc !important;margin-left: 3px;">(opzionale)</span>
                </div>
            </div>

            <div class="input-group mb-3 mt-3">
                <span class="input-group-text" id="basic-link">Link</span>
                <input class="form-control"  type="url" name="link" id="link" placeholder="https://www.youtube.com/embed/aSB77Wwfo7k">
            </div>

            <input type="hidden" name="code">

            <input type="hidden" name="controllerAction" value="Templates.create">

            <button onclick="submitNewTemplate()" class="btn btn-primary w-100">create</button>
        </form>
    </div>
</div>


