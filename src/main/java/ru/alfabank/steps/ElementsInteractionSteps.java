/**
 * Copyright 2017 Alfa Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.alfabank.steps;

import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadPropertyInt;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadValueFromFileOrPropertyOrVariableOrDefault;

/**
 * Шаги для взаимодействия с элементами страницы, доступные по умолчанию в каждом новом проекте
 * */

@Slf4j
public class ElementsInteractionSteps extends BaseMethods {

    /**
     * На странице происходит клик по заданному элементу
     */
    @И("^выполнено нажатие на (?:кнопку|поле|блок) \"([^\"]*)\"$")
    public void clickOnElement(String elementName) {
        akitaScenario.getCurrentPage().getElement(elementName).click();
    }

    /**
     * Сохранение значения элемента в переменную
     */
    @Когда("^значение (?:элемента|поля) \"([^\"]*)\" сохранено в переменную \"([^\"]*)\"$")
    public void storeElementValueInVariable(String elementName, String variableName) {
        akitaScenario.setVar(variableName, akitaScenario.getCurrentPage().getAnyElementText(elementName));
        akitaScenario.write("Значение [" + akitaScenario.getCurrentPage().getAnyElementText(elementName) + "] сохранено в переменную [" + variableName + "]");
    }

    /**
     * Выполняется наведение курсора на элемент
     */
    @Когда("^выполнен ховер на (?:поле|элемент) \"([^\"]*)\"$")
    public void elementHover(String elementName) {
        SelenideElement field = akitaScenario.getCurrentPage().getElement(elementName);
        field.hover();
    }

    /**
     * Нажатие на элемент по его тексту (в приоритете: из property, из переменной сценария, значение аргумента)
     */
    @И("^выполнено нажатие на элемент с текстом \"(.*)\"$")
    public void findElement(String text) {
        $(By.xpath(getTranslateNormalizeSpaceText(getPropertyOrStringVariableOrValue(text)))).click();
    }

    /**
    * Выполняется нажатие на кнопку и подгружается указанный файл
    * Селектор кнопки должны быть строго на input элемента
    * Можно указать путь до файла. Например, src/test/resources/example.pdf
    */
    @Когда("^выполнено нажатие на кнопку \"([^\"]*)\" и загружен файл \"([^\"]*)\"$")
    public void clickOnButtonAndUploadFile(String buttonName, String fileName) {
        String file = loadValueFromFileOrPropertyOrVariableOrDefault(fileName);
        File attachmentFile = new File(file);
        akitaScenario.getCurrentPage().getElement(buttonName).uploadFile(attachmentFile);
    }
}