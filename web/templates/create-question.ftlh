<div class="container">
<form action="/pollweb/questions" method="get">
    <input type="hidden" name="survey" value="#{survey}"/>
    <input type="hidden" name="type" value="${type}"/>
    <div class="row">
        <div class="col-25">
        <label>Titolo:</label>
        </div>
        <div class="col-75">
        <input type="text" name="text" placeholder="Inserire il titolo della domanda" required/>
        </div>
    </div>

    <div class="row">
        <div class="col-25">
        <label>Codice:</label>
        </div>
        <div class="col-75">
        <input type="text" name="code" placeholder="Inserire il codice identificativo" required/>
        </div>
    </div>

    <div class="row">
        <div class="col-25">
        <label>Nota:</label>
        </div>
        <div class="col-75">
        <textarea name="note" placeholder="Inserire informazioni utili alla compilazione della domanda" ></textarea>
        </div>
    </div><br>

 <div class="row">
    <div class="col-25">
    <input type="checkbox" name="mandatory" checked >Obbligatoria<br>
    </div>
</div>
   
    
    <#if type == "short text">

        <div class="row">
            <div class="col-25">
            <label>Minimo numero di caratteri:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="min-num-char" /><br>
            </div>
        </div>

        <div class="row">
            <div class="col-25">
            <label>Massimo numero di caratteri:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="max-num-char" /><br>
            </div>
        </div>

        <div class="row">
            <div class="col-25">
            <label>Pattern: </label>
            </div>
            <div class="col-75">
            <input type="text" name="pattern" /><br>
            </div>
        </div>
    <#elseif type == "long text">
        <div class="row">
            <div class="col-25">
            <label>Minimo numero di caratteri:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="min-num-char" /><br>
            </div>
        </div>
         <div class="row">
            <div class="col-25">
            <label>Massimo numero di caratteri:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="max-num-char" /><br>
            </div>
        </div>
    <#elseif type == "date">
        <div class="row">
            <div class="col-25">
            <label>Data minima:</label>
            </div>
            <div class="col-75">
            <input type="date" name="min-date" /><br>
            </div>
        </div><br>

        <div class="row">
            <div class="col-25">
            <label>Data massima:</label>
            </div>
            <div class="col-75">
            <input type="date" name="max-date" /><br>
            </div>
        </div>

    <#elseif type == "number">
        <div class="row">
            <div class="col-25">   
            <label>Minimo numero:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="min-num" /><br>
            </div>
        </div>
        <div class="row">
            <div class="col-25"> 
            <label>Massimo numero:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="max-num" /><br>
            </div>
        </div>
    <#elseif type == "choice">
        <div class="row">
            <div class="col-25">    
            <label>Minimo numero di scelte:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="min-num-choices" /><br>
            </div>
        </div>
        
        <div class="row">
            <div class="col-25">
            <label>Massimo numero di scelte:</label>
            </div><br>
            <div class="col-75">
            <input type="number" name="max-num-choices" /><br>
            </div>
        </div>

        <div class="row">
            <div class="col-25">
            <label>Scelte: </label>
            </div>
            <div class="col-75">
            <textarea required name="choices"  placeholder=" Inserire le scelte separate da ' ; ' "></textarea>
             </div>
        </div>
        </#if>
        <div class="row">
            <div class="col-25"></div>
            <div class="col-75">
            <input type="submit" class="btn" name="create" value="Conferma"/>
            </div>
        </div> 
</form>
</div>