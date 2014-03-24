<%@ Page Language="C#" AutoEventWireup="true"  MasterPageFile="~/Site.Master" CodeBehind="ParkingManagement.aspx.cs" Inherits="ParkingManagement.WebContent.ParkingManagement.ParkingManagement" %>

      


<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
           <h1>Parking Management under construction</h1>
         <ul style="list-style-type: none;">
             <li>
                 <ul style="list-style-type: none;">
                     <li>
                         <br />
                         <asp:Label ID="Label2" runat="server" Font-Size="Medium" Height="32px" Text="Manage Parking Lot: "></asp:Label>
                         <br />
                         <asp:DropDownList ID="DropDownList1" runat="server" OnSelectedIndexChanged="DropDownList1_SelectedIndexChanged">
                             <asp:ListItem Selected="True">Select Lot</asp:ListItem>
                             <asp:ListItem>Lot A</asp:ListItem>
                             <asp:ListItem>Lot B</asp:ListItem>
                             <asp:ListItem>Lot C</asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:DropDownList ID="DropDownList2" runat="server">
                             <asp:ListItem>Action:</asp:ListItem>
                             <asp:ListItem>Edit Lot</asp:ListItem>
                             <asp:ListItem></asp:ListItem>
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:DropDownList ID="DropDownList3" runat="server">
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Label ID="Label3" runat="server" Font-Size="Small" Text="Set Status:"></asp:Label>
                         &nbsp;&nbsp;&nbsp;
                         <asp:DropDownList ID="DropDownList4" runat="server">
                         </asp:DropDownList>
                         <br />
                     </li>
                     <li>----Lot information and/or diagram, if needed----<br />
&nbsp;<br />
&nbsp;</li>
                     <li>To Include: free individual parking space or change it&#39;s status<ul style="list-style-type: none;">
                         <li>Include &quot;insert user&quot; option if marking it in use</li>
                         </ul>
                     </li>
                     <li>
                         <br />
                         <asp:Button ID="Button5" runat="server" Text="Insert New Lot:" OnClick="Button5_Click" />
                         <br />
                         <br />
                         TO MAKE THE MAP LOAD WITH CORRECT SIZING: create an image 450 pixels tall by 1 pixel wide, and put it next to the map.<br />
                         <br />
                         <br />
                         <asp:Panel ID="Panel1" runat="server" Height="460px">
                             <asp:Label ID="Label4" runat="server" Height="450px" Width="5px"></asp:Label>
                         <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d13139.34871349347!2d-84.46062709999998!3d39.03338469999997!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8841b06e0b8b16e9%3A0x3ee280bce0f69454!2sNorthern+Kentucky+University!5e1!3m2!1sen!2sus!4v1395693200756" width="600" height="450" frameborder="0" style="border:0"></iframe>
                             <br />


                         </asp:Panel>
                         <br />
                         <!--           <iframe width="600"  height="450"  frameborder="0" style="border:0"  src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCLrr7e84uD7p0aV3fXjSY6mjG7tePIIbk&q=Space+Needle,Seattle+WA"></iframe>    -->
                     </li>
                     <li>
                         <br />
&nbsp;</li>
                 </ul>
             </li>
         </ul>
        </div>
    </section>
</asp:Content>