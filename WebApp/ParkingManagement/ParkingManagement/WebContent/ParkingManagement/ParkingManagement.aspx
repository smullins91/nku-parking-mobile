<%@ Page Language="C#" AutoEventWireup="true"  MasterPageFile="~/Site.Master" CodeBehind="ParkingManagement.aspx.cs" Inherits="ParkingManagement.WebContent.ParkingManagement.ParkingManagement" %>

      
<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
           <h1>Parking Management under construction</h1>
         <ul style="list-style-type: none;">
             <li>
                 <ul style="list-style-type: none;">
                     <li>
                         <asp:Label ID="Label1" runat="server" Font-Size="Medium" Height="30px" Text="Manage Users"></asp:Label>
                         <br />
                         <asp:Button ID="Button1" runat="server" Text="Add" />
                         <asp:Button ID="Button2" runat="server" OnClick="Button2_Click" Text="Edit" />
                         <asp:Button ID="Button3" runat="server" Text="Delete" />
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:TextBox ID="TextBox1" runat="server" Width="183px">User ID here</asp:TextBox>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:Button ID="Button4" runat="server" OnClick="Button4_Click" Text="Reset Password" Visible="False" />
                         <br />
                     </li>
                     <li>-----User information (if needed)----<br />
&nbsp;<br />
&nbsp;<br />
                     </li>
                     <li></li>
                     <li>
                         <br />
                         <asp:Label ID="Label2" runat="server" Font-Size="Medium" Height="32px" Text="Manage Parking"></asp:Label>
                         <br />
                         <asp:DropDownList ID="DropDownList1" runat="server">
                         </asp:DropDownList>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <asp:DropDownList ID="DropDownList2" runat="server">
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
                     <li></li>
                     <li>
                         <br />
                         <asp:Button ID="Button5" runat="server" Text="Insert New Lot:" />
                     </li>
                     <li>------MAP GOES HERE------<br />
&nbsp;</li>
                     <li>
                         <br />
&nbsp;</li>
                 </ul>
             </li>
         </ul>
        </div>
    </section>
</asp:Content>